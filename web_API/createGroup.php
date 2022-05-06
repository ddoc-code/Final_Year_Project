<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//group details from POST
$SQLeventID = $_POST["eventID"];
$SQLtitle = mysqli_real_escape_string($conn, $_POST["title"]); //escape user input
$SQLdescription = mysqli_real_escape_string($conn, $_POST["description"]); //escape user input
$SQLmaxPeople = $_POST["maxPeople"];
$SQLcurrentPeople = $_POST["currentPeople"];
$SQLattendees = $_POST["attendees"];
$SQLcreator = $_POST["creator"];
$SQLcreatorID = $_POST["creatorID"];

//testing variables
// $SQLeventID = 3;
// $SQLtitle = "TITLE2";
// $SQLdescription = "DESCRIPTION2";
// $SQLmaxPeople = 7;
// $SQLcurrentPeople = 1;
// $SQLattendees = "TESTUSER2";
// $SQLcreator = "TESTUSER2";
//$SQLcreatorID = 2;

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//SQL query to insert record into groups table
	$q = "INSERT INTO groups (id, eventID, title, description, maxPeople, currentPeople, attendees, creator, creatorID) VALUES (NULL, '$SQLeventID', '$SQLtitle', '$SQLdescription', '$SQLmaxPeople', '$SQLcurrentPeople', '$SQLattendees', '$SQLcreator', '$SQLcreatorID')";
    
    if ($conn->query($q) === TRUE) {
        echo "New group record created successfully";
    } else {
        echo "Error: " . $q . "<br>" . $conn->error;
    }
    
}
else {
	echo "CONNECTION FAILED";
}

$conn->close();
?> 