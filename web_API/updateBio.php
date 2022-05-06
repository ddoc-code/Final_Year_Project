<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//user id & new bio
$userID = $_POST["id"];
$newBio = $_POST["bio"];

//testing variables
#$userID = 1;
#$newBio = "";

// echo "id: ";
// echo $userID;
// echo "<br>bio: ";
// echo $newBio;
// echo "<br>";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query
	$q = "UPDATE users SET bio = '$newBio' WHERE id = $userID";
	
	//check if record was updated successfully
	if (mysqli_query($conn, $q)) {
		//successful
		$response["success"] = 1;
		$response["message"] = "Update successful";
		
		//return JSON response
		echo json_encode($response);
	}
	else {
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