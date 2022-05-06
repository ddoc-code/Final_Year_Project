<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//request details from POST
$SQLsenderID = $_POST["senderID"];
$SQLrecipientID = $_POST["recipientID"];
$SQLgroupID= $_POST["groupID"];
$SQLmessage = mysqli_real_escape_string($conn, $_POST["message"]); //escape user input
$SQLseen = $_POST["seen"];
    
//testing variables
// $SQLsenderID = 5;
// $SQLrecipientID = 1;
// $SQLgroupID = 1;
// $SQLmessage = "TEST REQUEST MESSAGE"; //escape user input
// $SQLseen = 0;

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//SQL query to insert record into requests table
	$q = "INSERT INTO requests (id, senderID, recipientID, groupID, message, seen, response) VALUES (NULL, '$SQLsenderID', '$SQLrecipientID', '$SQLgroupID', '$SQLmessage', '$SQLseen', NULL)";
    
    if ($conn->query($q) === TRUE) {
        echo "New request record created successfully";
    } else {
        echo "Error: " . $q . "<br>" . $conn->error;
    }
    
}
else {
	echo "CONNECTION FAILED";
}

$conn->close();
?> 