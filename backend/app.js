const express = require("express");
const { read } = require("fs");
const app = express();
const mongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017";
//const url = "mongodb://localhost:27017/app"

app.use(express.json()); //enable json parsing

const server = app.listen(3000);
/*
const server = app.listen(3000, () => {
    console.log("Listening on port 3000...")
});
*/
module.exports = server;

var myDb;
var collection;

mongoClient.connect(url, {
    useNewUrlParser : true,
    useUnifiedTopology : true,
    },(err, db) => {

    if (err) {
        //console.log("Error while connecting mongo client");
    } else {
        myDb = db.db("myDb");
        collection = myDb.collection("myTable");

        /*const listOfIncidents = {incidentList : [
            {
                title: "Example1",
                severity: 5,
                latitude: 37.4567,
                longitude: -122.11475
            },
            {
                title: "Example2",
                severity: 3,
                latitude: 37.47347,
                longitude: -122.1349
            },
            {
                title: "Example3",
                severity: 2,
                latitude: 37.4631,
                longitude: -122.13859,
            }
        ]}

        collection.insertOne(listOfIncidents)*/
/*
        collection.insertOne({
            title: "Example1",
            severity: 5,
            latitude: 37.4567,
            longitude: -122.11475
        })

        collection.insertOne({
            title: "Example2",
            severity: 3,
            latitude: 37.47347,
            longitude: -122.1349
        })

        collection.insertOne({
            title: "Example3",
            severity: 2,
            latitude: 37.4631,
            longitude: -122.13859,
        })

*/
        collection.find({}).toArray((err, result) => {
            if (err){
                throw err;
            }
            //console.log(result)
        }); 

        app.get("/incident", async (req, res) => {
            collection.find({}).toArray((err, result) => {
                if (err){
                    throw err;
                }
                //console.log(result)
                res.send(result);
                //collection.deleteMany()
                });
        });
        
        app.post("/incident", async (req, res) => {

            const newIncident = {
                title: req.body.title,
                severity: parseInt(req.body.severity, 10),
                latitude: parseFloat(req.body.latitude),
                longitude: parseFloat(req.body.longitude)
            };
            /*
            console.log(newIncident.title)
            console.log(newIncident.severity)
            console.log(newIncident.latitude)
            console.log(newIncident.longitude)
            */
            collection.insertOne(newIncident);
            res.status(200).send();
        });        

        //this function is used exclusively to close the
        //database connection and clearing it for testing
        //Also closes the app.listen port
        app.delete("/incident", async (req, res) => {
            await collection.deleteMany();
            res.send("database cleared");
            db.close();
            server.close();
        });

    }
});






//module.exports = app;
