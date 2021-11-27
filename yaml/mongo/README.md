Access to the shell of mongo db and execute this command

1. var mydb = connect('mongodb://admin:password@localhost:27017/')
2. mydb.createCollection(COLLECTION_NAME, { capped: true, size: 100000 })