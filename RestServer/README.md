
REST server url to use:

Listing controller info and connected server:

    http://localhost:8080/controller

Getting sevo ID 1 info

    http://localhost:8080/servo/1


Getting servo's 1 present position

    http://localhost:8080/servo/1?param=getPresentPosition


Setting servo's 1 goal position

    curl -i -X PUT "http://localhost:8080/servo/1?paratGoalPosition&value=450"

*Note: there can be only parameters with int value set over RESTFul URL
 

