const express = require('express')
const dotenv = require('dotenv');
const { v4: uuidv4 } = require('uuid');
const cors = require("cors");
const db = require("./utils/db");
const multer = require('multer');
const path = require('path');
const fs = require('fs');
const uniqid = require('uniqid');
const cloudinary = require('cloudinary').v2;
const news_api = require('./utils/news_api');
const Notification = require('./models/notifications');
const http = require('http');
const io = require('socket.io')(http);
const app = express();
app.use(express.json());

// /--------------------------------------------------------------------------------------------\
//                                     Env Configuration
// \--------------------------------------------------------------------------------------------/

dotenv.config({ path: './.env' });

// /--------------------------------------------------------------------------------------------\
//                                     Port Configuration
// \--------------------------------------------------------------------------------------------/

const port = process.env.PORT || 8000;

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
            if (limit < reverse_collection.length) {
                for (let i = 0; i < limit; i++) {
                    final_data.push(reverse_collection[i]);
                }
                await db.db.disconnect();
                res.status(200).json(final_data);
            } else {
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
            console.log(doc['filter']);
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
                        res.status(200).json({ msg: "Data inserted with image", stat: 1, secure_url: result.secure_url, url: result.url, original_filename: result.original_filename, width: result.width, height: result.height, format: result.format, resourse_type: result.resource_type, bytes: result.bytes });
                    } else {
                        await db.db.disconnect();
                        res.json({ stat: 0, msg: "Collection not exists." });
                    }

                } catch (error) {
                    res.json({ stat: 0, msg: error });
                }

            }, (err) => {
                res.json({ stat: 0, msg: "Failed to upload" });
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

// /--------------------------------------------------------------------------------------------\
//                                  Realtime chatting API
// \--------------------------------------------------------------------------------------------/

const clients = new Map();

app.get('/chat-updates/:collection_name', async (req, res) => {
    const clientId = uuidv4();

    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.flushHeaders();

    clients.set(clientId, res);

    req.on('close', () => {
        clients.delete(clientId);
    });

    try {
        const collection_name = req.params.collection_name;
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find().toArray();
            await db.db.disconnect();

            collection_data.forEach((message) => {
                // res.write(`data: ${JSON.stringify(message)}\n\n`);
                res.write(JSON.stringify(message));
            });
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }

    } catch (error) {
        console.error('Error fetching chat messages:', error);
    }
});


app.post('/send-message/:collection_name', async (req, res) => {
    try {
        const collection_name = req.params.collection_name;
        const doc = req.body;
        // console.log(doc);
        await db.db.connect();
        // console.log(doc);
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection) {
            const collection = db.db.Connection.collection(collection_name);
            await collection.insertOne(doc);
            const collection_data = await collection.find().toArray();
            await db.db.disconnect();
            for (const [clientId, clientResponse] of clients) {
                // clientResponse.write(`data: ${JSON.stringify(doc)}\n\n`);
                clientResponse.write(JSON.stringify(doc));
            }
            res.sendStatus(200);
            // res.status(200).json({ stat: 1, msg: "Done" });
        } else {
            await db.db.disconnect();
            res.json({ stat: 0, msg: "Collection not exists." });
        }


    } catch (error) {
        console.error('Error saving chat message:', error);
        res.sendStatus(500);
    }
});

app.get('/close-connections', (req, res) => {
    try {
        for (const [clientId, clientResponse] of clients) {
            clientResponse.end();
            clients.delete(clientId);
        }
        res.sendStatus(200);
    } catch (error) {
        console.error('Error closing connections:', error);
        res.sendStatus(500);
    }
});

// /--------------------------------------------------------------------------------------------\
//                              Message Notification Using Model
// \--------------------------------------------------------------------------------------------/

app.post('/store-message/:userid', async (req, res) => {
    try {
        const userid = req.params.userid;
        const doc = req.body;
        await db.db.connect();
        const is_exist = await Notification.notification.findOne({ 'userId': userid });
        // console.log(is_exist);
        if (!is_exist) {
            const notification = new Notification.notification({
                userId: userid,
                all_notifications: [doc]
            });
            const saved_notification = await notification.save();
        }else{
            await is_exist.all_notifications.push(doc);
            await is_exist.save();
        } 
        await db.db.disconnect();
        res.sendStatus(200);
    } catch (error) {
        res.status(500).send("Internal Server Error");
    }
})

app.get('/get-notification/:userid/:field_name', async (req, res) => {
    try {
        const userid = req.params.userid;
        const field_name = req.params.field_name;
        await db.db.connect();
        const is_exist = await Notification.notification.findOne({ 'userId': userid });
        // console.log(is_exist);
        if (!is_exist) {
            await db.db.disconnect();
            res.json({stat:0, msg:"user id not exists"});
        }else{
            if(is_exist[field_name]){
                await db.db.disconnect();
                res.json({data : is_exist[field_name]})
            }else{
                await db.db.disconnect();
                res.json({stat:0, "msg":'field name not exists'})
            }     
        } 
    } catch (error) {
        res.status(500).send("Internal Server Error");
    }
})

app.get('/', async (req, res) => {
    res.status(200).send({ msg: "Connected to SIH Project!!" });
})
app.listen(port, () => {
    console.log(`Listening on port ${port}`)
})