About the issues I faced during the task: For most parts I did not face any major issues some things that I did not know about
android having not worked on it for last two years. I was unaware about some of the functions of the view. One thing that took a great portion of the time to implement this application was connecting to internet on a different thread. Having worked on primarily webapps for the past year I was not used to this and was not aware of the async connection available in android and started implementing my on thread for that purpose. I later found this and was able to use it.

Some of the things that I have not implemented are:
Readable messages in case the JSON parsing fails.
If there are any changes in the schema, the app will just crash, without a readable message.

As for the cart implementation, in a full implementation first I will need to log in the user, create a connection to the server
and use the connection to 
The mock API does not seem to support complete user identification it is a matter of simply adding 
elements using the api and parsing the response to get the current state of the UI.

I will need to add a button to the project page that uses the post api to add elements to cart, also add a button to go to the cart 
activity directly and in the cart use a list to display the elements in the cart and on clicking an element send the user to a page
that lets the user delete the item (may be provided in the list itself).

On deletion I will have to reload the cart activity.

Also, if it were a working activity, I would have to create a connection using the session api provided, check authorization
before accessing the cart and checkout.
