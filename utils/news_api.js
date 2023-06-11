const { PythonShell } = require('python-shell');
const path = require('path');
const fs = require('fs');
const db = require('./db')
const { parse } = require('dotenv');


async function scrap_data(req, res) {
    const data = await PythonShell.run(path.join(__dirname, '../python_news_scrapper/news_scraper.py'), null);
    console.log(data)
    if (data[data.length - 1]) {
        // console.log(data)
        if (fs.existsSync(path.join(__dirname, '../assets/scrap_data.json'))) {
            const file = fs.readFileSync(path.join(__dirname, '../assets/scrap_data.json'))
            const parse_data = JSON.parse(file)
            fs.unlinkSync(path.join(__dirname, '../assets/scrap_data.json'));
            try {
                const collection_name = req.params.collection_name;
                await db.db.connect();
                let collection = await db.db.Connection.db.listCollections({ name: collection_name }).next();
                if (collection) {
                    let reverse_agri_news = parse_data['agri_news'].reverse();
                    const collection = db.db.Connection.collection(collection_name); 
                    const collection_data = await collection.find().toArray();
                    // const reverse_collection_data = collection_data.reverse();
                    if (collection_data.length == 0){
                        await collection.insertMany(reverse_agri_news);
                        await db.db.disconnect();
                        res.status(200).json({ stat: 1, msg: "Done" });
                    }else if(collection_data.length > reverse_agri_news.length){
                        for(let i = 0; i<reverse_agri_news.length;i++){
                            if (reverse_agri_news[i].heading !== collection_data[i].heading){
                                await collection.insertOne(reverse_agri_news[i]);
                            }
                        }
                        await db.db.disconnect();
                        res.status(200).json({ stat: 1, msg: "Done" });
                    }else{
                        for(let i = 0; i<collection_data.length;i++){
                            if (reverse_agri_news[i].heading !== collection_data[i].heading){
                                await collection.insertOne(reverse_agri_news[i]);
                            }
                        }
                        for(let i = collection_data.length; i<reverse_agri_news.length;i++){
                            if (reverse_agri_news[i].heading !== collection_data[i].heading){
                                await collection.insertOne(reverse_agri_news[i]);
                            }
                        }
                        await db.db.disconnect();
                        res.status(200).json({ stat: 1, msg: "Done" });
                    }  
                    
                } else {
                    await db.db.disconnect();
                    res.json({ stat: 0, msg: "Collection not exists." });
                }
        
            } catch (error) {
                await db.db.disconnect();
                res.status(500).send("Internal Server Error");
            }
        } else {
            res.send("Some Error Occured")
        }

    }
    else {
        res.send("Server Error");
    }

}

module.exports = scrap_data;