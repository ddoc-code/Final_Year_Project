<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//new user details from POST
$SQLusername = mysqli_real_escape_string($conn, $_POST["username"]); //escape user input
$SQLpassword = mysqli_real_escape_string($conn, $_POST["password"]); //escape user input
$SQLemail = mysqli_real_escape_string($conn, $_POST["email"]); //escape user input
$SQLlocation = mysqli_real_escape_string($conn, $_POST["location"]); //escape user input
    
//testing variables
// $SQLusername = "TESTUSER";
// $SQLpassword = "pw1";
// $SQLemail = "testuser@example.com";
// $SQLlocation = "New Cross";

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//SQL query to insert record into messages table
	$q = "INSERT INTO users (id, username, password, email, location, bio, interests) VALUES (NULL, '$SQLusername', '$SQLpassword', '$SQLemail', '$SQLlocation', '', '')";
    
    if ($conn->query($q) === TRUE) {
        echo "New user record created successfully";
    } else {
        echo "Error: " . $q . "<br>" . $conn->error;
    }
    
}
else {
	echo "CONNECTION FAILED";
}

$conn->close();
?> 