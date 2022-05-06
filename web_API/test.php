<?php
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//$loginuser = $_POST["user"];
//$loginpass = $_POST["pass"];
$test1 = "admin";
$test2 = "pw1";

$conn = mysqli_connect($host, $user, $pass, $db, $port);

if($conn) {
	echo "CONNECTION SUCCESSFUL";
	
	/*
	$q = "SELECT * FROM test";
	$result = mysqli_query($conn, $q);
	
	if (mysqli_num_rows($result) > 0){
		echo "RESULTS FOUND";
	}
	else {
		echo "RESULTS NOT FOUND";
	}
	*/
	
	/*
	$q = "SELECT * FROM test WHERE username = '$test1' AND password = '$test2'";
	$result = mysqli_query($conn, $q);
	$row = mysqli_fetch_array($result);
	$data = $row[1];
	
	if ($data) {echo $data;}
	*/
	
	//array for JSON response
	$response = array();
	
	//SQL query
	$q = "SELECT * FROM users WHERE username = '$test1' AND password = '$test2'";
	
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