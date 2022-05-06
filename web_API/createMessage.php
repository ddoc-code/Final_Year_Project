<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//message details from POST
$SQLgroupID = $_POST["groupID"];
$SQLuserID = $_POST["userID"];
$SQLuser = $_POST["user"];
$SQLtext = mysqli_real_escape_string($conn, $_POST["text"]); //escape user input
$SQLdeleted = $_POST["deleted"];
    
//testing variables
// $SQLgroupID = 1;
// $SQLuserID = 3;
// $SQLuser = "user1";
// $SQLtext = "This is another test message!"; //escape user input
// $SQLdeleted = 0;

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//SQL query to insert record into messages table
	$q = "INSERT INTO messages (id, groupID, userID, user, text, deleted) VALUES (NULL, '$SQLgroupID', '$SQLuserID', '$SQLuser', '$SQLtext', '$SQLdeleted')";
    
    if ($conn->query($q) === TRUE) {
        echo "New message record created successfully";
    } else {
        echo "Error: " . $q . "<br>" . $conn->error;
    }
    
}
else {
	echo "CONNECTION FAILED";
}

$conn->close();
?> 