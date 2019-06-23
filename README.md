This repository provides Client side Implementation of the <a href="https://github.com/Nancy-Chauhan/keystore">Project</a>.
A user can perform set(key, val) and get(key) operations over HTTP and also subscribe to changes happening to any of the keys.

## Libraries used

1. <a href="https://square.github.io/retrofit/">Retrofit</a>
2. OkHttp3 

## Building the project

Clone the repository and generate a compiled shadow jar under build/libs by following command :
```
./gradlew build 
```
To run the jar use the command : 

`java -jar build/libs/keystore-client-all.jar`

or 

unzip the distribution found in `build/distributions/keystore-client-shadow.zip` and run `bin/keystore-client`

## Usage 

```
keystore-client COMMAND args...

Where COMMAND may be:
    get-all              Gets all key value stored in the server
    get    <key>         Fetches the value of the given key from keystore server.
    set    <key> <value> Set the value of the key in keystore server.
    delete <key>         Deletes the given key from keystore server.
    watch                Watches keystore server for changes
```
1) Set the value of the key in keystore server with `java -jar build/libs/keystore-client-all.jar set <key> <value>`

2) Get all key value stored in the server with `java -jar build/libs/keystore-client-all.jar get-all`

3) Fetches the value of the given key from keystore server with `java -jar build/libs/keystore-client-all.jar get <key>`

4) Deletes the given key from keystore server with `java -jar build/libs/keystore-client-all.jar delete <key>`

5) Watches keystore server for changes in sepearte terminal tab with `java -jar build/libs/keystore-client-all.jar watch`
