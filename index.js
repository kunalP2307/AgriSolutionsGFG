const express = require('express')
const dotenv = require('dotenv');
const cors = require("cors");
const db = require("./utils/db");
const app = express()
app.use(express.json());
dotenv.config({ path: './.env' });
const port = process.env.PORT || 7000;
const corsOptions = {
    origin: '*',
    credentials: true,            //access-control-allow-credentials:true
    optionSuccessStatus: 200,
}

app.use(cors(corsOptions)) // Use this after the variable declarationd

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
        console.log(collection);
        if (collection) {
            await db.db.disconnect();
            res.status(200).json({stat: 0, msg:"Collection already exists"});
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
        }else{
            await db.db.disconnect();
            res.status(200).json({stat:0, msg:"Collection not exists"});
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
        console.log(doc);
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            await collection.insertOne(doc);
            await db.db.disconnect();
            res.status(200).json({stat:1, msg:"Done"});
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        console.log(doc);
        await db.db.connect();
        let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            await collection.insertMany(doc);
            await db.db.disconnect();
            res.status(200).json({stat:1, msg:"Done"});
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find().toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find(doc).toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.find({$or: array_doc}).toArray();
            await db.db.disconnect();
            res.status(200).json(collection_data);
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.updateOne(array_doc['filter'], {$set:array_doc['update']});
            console.log(collection_data)
            await db.db.disconnect();
            if(collection_data){
                res.status(200).json({stat:1, msg:"Done"});
            }else{
                res.status(200).json({stat:0, msg:"Failed"});
            }
            
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.updateMany(array_doc['filter'], {$set:array_doc['update']});
            console.log(collection_data)
            await db.db.disconnect();
            if(collection_data){
                res.status(200).json({stat:1, msg:"Done"});
            }else{
                res.status(200).json({stat:0, msg:"Failed"});
            }
            
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.deleteOne(doc['filter']);
            console.log(collection_data)
            await db.db.disconnect();
            if(collection_data){
                res.status(200).json({stat:1, msg:"Done"});
            }else{
                res.status(200).json({stat:0, msg:"Failed"});
            }
            
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
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
        if (collection){
            const collection = db.db.Connection.collection(collection_name);
            const collection_data = await collection.deleteMany(doc['filter']);
            console.log(collection_data)
            await db.db.disconnect();
            if(collection_data){
                res.status(200).json({stat:1, msg:"Done"});
            }else{
                res.status(200).json({stat:0, msg:"Failed"});
            }
            
        }else{
            await db.db.disconnect();
            res.json({stat:0, msg:"Collection not exists."});
        }
    } catch (error) {
        await db.db.disconnect();
        res.status(500).send("Internal Server Error");
    }
})



app.get('/', async (req, res) => {
    res.status(200).send({ msg: "Connected to SIH Project!!" });
})
app.listen(port, () => {
    console.log(`Listening on port ${port}`)
})