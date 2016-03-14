To start ZooKeeper you need a configuration file. Here is a sample, create it in conf/zoo.cfg:

zoo.cfg file content 

```
tickTime=2000
dataDir=/var/lib/zookeeper
clientPort=2181

```

Now that you created the configuration file, you can start ZooKeeper:

```
bin/zkServer.sh start

```

Connecting to ZooKeeper

```
bin/zkCli.sh -server 127.0.0.1:2181

```

Once you have connected, you should see something like:

```

Connecting to localhost:2181
log4j:WARN No appenders could be found for logger (org.apache.zookeeper.ZooKeeper).
log4j:WARN Please initialize the log4j system properly.
Welcome to ZooKeeper!
JLine support is enabled
[zkshell: 0]
        

```

From the shell, type help to get a listing of commands that can be executed from the client, as in:

```
[zkshell: 0] help
ZooKeeper host:port cmd args
        get path [watch]
        ls path [watch]
        set path data [version]
        delquota [-n|-b] path
        quit
        printwatches on|off
        create path data acl
        stat path [watch]
        listquota path
        history
        setAcl path acl
        getAcl path
        sync path
        redo cmdno
        addauth scheme auth
        delete path [version]
        deleteall path
        setquota -n|-b val path

        

```


```
[zkshell: 8] ls /
[zookeeper]
        
  
[zkshell: 9] create /zk_test my_data
Created /zk_test      
        

[zkshell: 11] ls /
[zookeeper, zk_test]


```

Next, verify that the data was associated with the znode by running the get command, as in:


```

[zkshell: 12] get /zk_test
my_data
cZxid = 5
ctime = Fri Jun 05 13:57:06 PDT 2009
mZxid = 5
mtime = Fri Jun 05 13:57:06 PDT 2009
pZxid = 5
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0
dataLength = 7
numChildren = 0

```


We can change the data associated with zk_test by issuing the set command, as in:

```
[zkshell: 14] set /zk_test junk
cZxid = 5
ctime = Fri Jun 05 13:57:06 PDT 2009
mZxid = 6
mtime = Fri Jun 05 14:01:52 PDT 2009
pZxid = 5
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0
dataLength = 4
numChildren = 0
[zkshell: 15] get /zk_test
junk
cZxid = 5
ctime = Fri Jun 05 13:57:06 PDT 2009
mZxid = 6
mtime = Fri Jun 05 14:01:52 PDT 2009
pZxid = 5
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0
dataLength = 4
numChildren = 0

```

(Notice we did a get after setting the data and it did, indeed, change.

Finally, let's delete the node by issuing:


```
[zkshell: 16] delete /zk_test
[zkshell: 17] ls /
[zookeeper]
[zkshell: 18]

```
