Access to the shell of mongo db and execute this command

1. mongo
2. var mydb = connect('mongodb://admin:password@localhost:27017/')

Create collection
mydb.createCollection(COLLECTION_NAME, { capped: true, size: 100000 })

Search into collection
mydb.getCollection('COLLECTION_NAME').find()