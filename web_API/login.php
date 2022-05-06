<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//user login details
$loginuser = $_POST["user"];
$loginpass = $_POST["pass"];

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//change character set to utf8 - added from retrieveEvents.php 28/03/22
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query
	$q = "SELECT * FROM users WHERE username = '$loginuser' AND password = '$loginpass'";
	
	//retrieve user from mySQL DB
	$result = mysqli_query($conn, $q);
	
	
	//check for empty result
	if (!empty($result)) {
		if (mysqli_num_rows($result) > 0) {
			
			$result = mysqli_fetch_array($result);
			
			//create user array and add details
			$user = array();
			$user["id"] = $result["id"];
			$user["username"] = $result["username"];
			$user["password"] = $result["password"];
			$user["email"] = $result["email"];
			$user["location"] = $result["location"];
			$user["bio"] = $result["bio"];
			$user["interests"] = $result["interests"];
			
			//successful
			$response["success"] = 1;
			
			//create response array
			$response["user"] = array();
			//add user array to response
			array_push($response["user"], $user);
			
			//return JSON response
			echo json_encode($response);
		}
		else {
			//user not found
			$response["success"] = 0;
			$response["message"] = "Failed to log in";
			
			//return JSON response (unsuccessful)
			echo json_encode($response);
		}
	}
	else {
		//user not found
		$response["success"] = 0;
		$response["message"] = "Failed to log in";
		
		//return JSON response (unsuccessful)
		echo json_encode($response);
	}
}
else {
	echo "CONNECTION FAILED";
}

?>