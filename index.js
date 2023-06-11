const express = require('express')
const dotenv = require('dotenv');
const cors = require("cors");
const db = require("./utils/db");
const multer = require('multer');
const path = require('path');
const fs = require('fs');
const uniqid = require('uniqid');
const cloudinary = require('cloudinary').v2;
const news_api = require('./utils/news_api');
const app = express();
app.use(express.json());

// /--------------------------------------------------------------------------------------------\
//                                     Env Configuration
// \--------------------------------------------------------------------------------------------/

dotenv.config({ path: './.env' });

// /--------------------------------------------------------------------------------------------\
//                                     Port Configuration
// \--------------------------------------------------------------------------------------------/

const port = process.env.PORT || 7000;

// /--------------------------------------------------------------------------------------------\
//                                     Cors Configuration
// \--------------------------------------------------------------------------------------------/

const corsOptions = {
    origin: '*',
    credentials: true,            //access-control-allow-credentials:true
    optionSuccessStatus: 200,
}

app.use(cors(corsOptions)) // Use this after the variable declarationd

// /--------------------------------------------------------------------------------------------\
//                          Multer Configuration middleware to handle upload
// \--------------------------------------------------------------------------------------------/

const upload = multer();

// /--------------------------------------------------------------------------------------------\
//                                     Cloudinary Configuration
// \--------------------------------------------------------------------------------------------/

cloudinary.config({
    cloud_name: process.env.CLOUD_NAME,
    api_key: process.env.API_KEY,
    api_secret: process.env.API_SECRET,
    secure: true
});

// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Collections Operations
// \--------------------------------------------------------------------------------------------/

app.get('/get-collections', async (req, res) => {
    try {
        let collection_list = []
        await db.db.connect();
        const collections = await db.db.Connection.db.listCollections().toArray();
        collections.map((collection) => {
            collection_list.push(collection.name);
        })
        await db.db.disconnect();
        res.json(collection_list);
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.get('/create-collection/:collection_name', async (req, res) => {
    try {
        let collection_list = []
        const collection_name = req.params.collection_name;
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        // console.log(collection);
        if (collection) {
            await db.db.disconnect();
            res.status(200).json({ stat: 0, msg: "Collection already exists" });
        } else {
            const collection = await db.db.Connection.createCollection(collection_name);
            if (collection) {
                const collections = await db.db.Connection.db.listCollections().toArray();
                collections.map((collection) => {
                    collection_list.push(collection.name);
                })
            }
            await db.db.disconnect();
            res.json(collection_list);
        }

    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.get('/drop-collection/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        let collection_list = []
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            await db.db.Connection.db.dropCollection(collection_name);
            const collections = await db.db.Connection.db.listCollections().toArray();
            collections.map((collection) => {
                collection_list.push(collection.name);
            })
            await db.db.disconnect();
            res.status(200).json(collection_list);
        } else {
            await db.db.disconnect();
            res.status(200).json({ stat: 0, msg: "Collection not exists" });
        }

    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Insertion Operations
// \--------------------------------------------------------------------------------------------/

app.post('/insert-one/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        const doc = req.body;
        // console.log(doc);
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            await collection.insertOne(doc);
            await db.db.disconnect();
            res.status(200).json({ stat: 1, msg: "Done" });
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.post('/insert-many/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        const doc = req.body;
        // console.log(doc);
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            await collection.insertMany(doc);
            await db.db.disconnect();
            res.status(200).json({ stat: 1, msg: "Done" });
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }

    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})


// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Find Operations
// \--------------------------------------------------------------------------------------------/

app.get('/find/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find().toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.get('/find-latest/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find().toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data.reverse());
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.get('/find-latest/:collection_name/:limit', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        const limit = req.params.limit;
        let final_data = [];
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find().toArray();
            const reverse_collection = collection_data.reverse();
            if(limit < reverse_collection.length){
                for(let i=0; i<limit;i++){
                    final_data.push(reverse_collection[i]);
                }
                await db.db.disconnect();
                res.status(200).json(final_data);
            }else{
                await db.db.disconnect();
                res.status(200).json(reverse_collection);
            }
            
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.post('/find-with-and/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find(doc).toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.post('/find-with-or/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const array_doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find({ $or: array_doc }).toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})



// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Update Operations
// \--------------------------------------------------------------------------------------------/

app.post('/update/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const array_doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.updateOne(array_doc['filter'], { $set: array_doc['update'] });
            console.log(collection_data)
            await db.db.disconnect();
            if (collection_data) {
                res.status(200).json({ stat: 1, msg: "Done" });
            } else {
                res.status(200).json({ stat: 0, msg: "Failed" });
            }

        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.post('/update-many/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const array_doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.updateMany(array_doc['filter'], { $set: array_doc['update'] });
            console.log(collection_data)
            await db.db.disconnect();
            if (collection_data) {
                res.status(200).json({ stat: 1, msg: "Done" });
            } else {
                res.status(200).json({ stat: 0, msg: "Failed" });
            }

        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})


// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Delete Operations
// \--------------------------------------------------------------------------------------------/

app.post('/delete/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.deleteOne(doc['filter']);
            console.log(collection_data)
            await db.db.disconnect();
            if (collection_data) {
                res.status(200).json({ stat: 1, msg: "Done" });
            } else {
                res.status(200).json({ stat: 0, msg: "Failed" });
            }

        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})

app.post('/delete-many/:collection_name', async (req, res) => {
    const collection_name = req.params.collection_name;
    const doc = req.body;
    try {
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.deleteMany(doc['filter']);
            console.log(collection_data)
            await db.db.disconnect();
            if (collection_data) {
                res.status(200).json({ stat: 1, msg: "Done" });
            } else {
                res.status(200).json({ stat: 0, msg: "Failed" });
            }

        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})


// /--------------------------------------------------------------------------------------------\
//                                     MongoDB Insertion with single image
// \--------------------------------------------------------------------------------------------/

async function buffer_to_image(buffer, outputPath, req, res) {
    const collection_name = req.params.collection_name;
    const doc = req.body;
    fs.writeFile(outputPath, buffer, (err) => {
      if (err) {
        console.error('Error writing image:', err);
      } else {
        cloudinary.uploader.upload(outputPath).then(async (result) => {
            // console.log(result);
            try {
                doc['img_url'] = result.secure_url;
                fs.unlinkSync(outputPath);
                // console.log(doc)
                await db.db.connect();
                let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
                if (collection) {
                    const collection = db.db.Connection.collection(collection_name);
                    await collection.insertOne(doc);
                    await db.db.disconnect();
                    // res.status(200).json({ stat: 1, msg: "Done" });
                    res.status(200).json({msg: "Data inserted with image", stat: 1, secure_url: result.secure_url, url: result.url, original_filename: result.original_filename, width:result.width, height: result.height, format: result.format, resourse_type: result.resource_type, bytes: result.bytes});
                } else {
                    await db.db.disconnect();
                    res.json({ stat: 0, msg: "Collection not exists." });
                }
                
            } catch (error) {
                res.json({stat:0, msg:error});
            }
            
        }, (err)=>{
            res.json({stat:0, msg:"Failed to upload"});
        })
        // console.log('Image successfully written to', outputPath);  
      }
    });

  }



app.post('/insert-image/:collection_name', upload.single('file'), async (req, res) => {
    try {
        // console.log(req.file);
        await buffer_to_image(req.file.buffer, path.join(__dirname, `./assets/${uniqid()}.${req.file.mimetype.split('/')[1]}`), req, res);
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})


// /--------------------------------------------------------------------------------------------\
//                                 News_API
// \--------------------------------------------------------------------------------------------/
app.get('/scrap-news/:collection_name', (req, res) => {
    news_api(req, res);
})


app.get('/', async (req, res) => {
    res.status(200).send({ msg: "Connected to SIH Project!!" });
})
app.listen(port, () => {
    console.log(`Listening on port ${port}`)
})