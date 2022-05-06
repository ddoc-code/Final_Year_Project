<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//requestID from post
$SQLrequestID = $_POST["requestID"];

//testing variables
#$SQLrequestID = 5;

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query
	$q = "UPDATE requests SET response = 0 WHERE id = $SQLrequestID";
	
	//check if record was updated successfully
	if (mysqli_query($conn, $q)) {
		//successful
		$response["success"] = 1;
		$response["message"] = "Update successful";
		
		//return JSON response
		echo json_encode($response);
	}
	else {
        //unsuccessful
		$response["success"] = 0;
		$response["message"] = mysqli_error($conn);
		
		//return JSON response
		echo json_encode($response);
	}
}	
else {
	echo "CONNECTION FAILED";
}

?>