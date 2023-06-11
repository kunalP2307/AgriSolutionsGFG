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
                    // let reverse_agri_news = [{"a":"sonu"},{"heading": "India's tea industry getting drowsy, needs some 'kadak' measures"}].reverse();
                    let agri_news = parse_data['agri_news'];
                    // let agri_news = [{'heading':'ram'},{"heading": "India's tea industry getting drowsy, needs some 'kadak' measures"},{'heading':"b"}, {'heading':'c'}];
                    const collection = db.db.Connection.collection(collection_name);
                    const collection_data = await collection.find().toArray();
                    const reverse_collection_data = collection_data.reverse();
                    if (reverse_collection_data.length == 0) {
                        await collection.insertMany(reverse_agri_news);
                        await db.db.disconnect();
                        res.status(200).json({ stat: 1, msg: "Done" });
                    }else if(agri_news.length < reverse_collection_data.length){
                        // console.log('k')
                        let final_news = [];
                        for(let i = 0; i<agri_news.length;i++){
                            let found = false;
                            for(let j=0; j<agri_news.length;j++){
                                if(agri_news[i].heading == reverse_collection_data[j].heading){
                                    found = true;   
                                }
                            }

                            if (!found) {
                                final_news.push(agri_news[i]);
                            }
                        }
                        // console.log(final_news);
                        if (final_news.length != 0){
                            await collection.insertMany(final_news.reverse());
                            await db.db.disconnect();
                            res.status(200).json({ stat: 1, msg: "Done" });
                        }else{
                            res.status(200).json({ stat: 1, msg: "Done" });
                        }
                        
                    }else{
                        // console.log('m')
                        let final_news = [];
                        for(let i = 0; i<agri_news.length;i++){
                            let found = false;
                            for(let j=0; j<reverse_collection_data.length;j++){
                                if(agri_news[i].heading == reverse_collection_data[j].heading){
                                    found = true; 
                                }
                            }
                            if (!found) {
                                final_news.push(agri_news[i]);
                            }
                        }
                        // console.log(final_news);
                        if (final_news.length != 0){
                            await collection.insertMany(final_news.reverse());
                            await db.db.disconnect();
                            res.status(200).json({ stat: 1, msg: "Done" });
                        }else{
                            res.status(200).json({ stat: 1, msg: "Done" });
                        }
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