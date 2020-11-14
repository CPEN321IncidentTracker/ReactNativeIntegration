//installs:
//npm install --save-dev jest
//npm install supertest --save-dev supertest
//npm install @shelf/jest-mongodb --dev
//npm install mongodb-memory-server --save-dev
//npm install mongodb-memory-server-core --save-dev

/*
const {MongoMemoryServer} = require('mongodb-memory-server');
const jestMongodbConfig =  require('./jest-mongodb-config');
const mongod = new MongoMemoryServer();
*/
const mongoClient = require("mongodb").MongoClient;

const { expect } = require("@jest/globals");

const App = require("./app");
const supertest = require("supertest");
const request = supertest(App);

describe("Simple test", () => {
    let connection;
    let db;
    let server;
    let agent;
    
    beforeAll(async () => {
        
        connection = await mongoClient.connect("mongodb://localhost:27017");
        //db = await connection.db(global.__MONGO_DB_NAME__);

    });

    afterAll(async (done) => {
        
        await connection.close(done);
        
        //await global.__MONGOD__.stop();
    });
    
    beforeEach(async () => {
        //to be added once more tests are implemented
    });

    afterEach( async () => {
        //to be added once more tests are implemented
    });

    const mockincidentlist = jest.fn();

    mockincidentlist.mockReturnValue([{"id" : "1", "title" : "mock1", "severity" : 5, 
                       "latitude" : 37.3631, "longitude" : -122.123},
                       {"id" : "2", "title" : "mock2", "severity" : 4, 
                       "latitude" : 37.222, "longitude" : -122.321},
                       {"id" : "3", "title" : "mock3", "severity" : 3, 
                       "latitude" : 37.444, "longitude" : -122.909}]);

    const mockincident1 = jest.fn();
    mockincident1.mockReturnValue({"title" : "mock1", "severity" : 5, 
                           "latitude" : 37.3631, "longitude" : -122.123});
    const mockincident2 = {"title" : "mock2", "severity" : 4, 
                           "latitude" : 37.222, "longitude" : -122.321};
    const mockincident3 = {"title" : "mock3", "severity" : 3, 
                           "latitude" : 37.444, "longitude" : -122.909};

    

    it("should return an array consisting of the mocked incident", 
        async (done) => {
        
        //const collection = db.collection('myTable');

        //await collection.insertOne(mockincident1);
        
        var result = await request.post("/incident").send(mockincident1());

        const mock1 = mockincident1();
        
        //console.log(result);
        const response = await request.get("/incident");
        //console.log(response.body);
        expect(response.body[0].title).toBe(mock1.title);
        expect(response.body[0].severity).toBe(mock1.severity);
        expect(response.body[0].latitude).toBe(mock1.latitude);
        expect(response.body[0].longitude).toBe(mock1.longitude);

        result = await request.delete("/incident");
        //console.log(mockincidentlist())
        done();
    });
});