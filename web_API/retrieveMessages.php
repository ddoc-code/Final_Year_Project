<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//group id from POST
$SQLgroupID = $_POST["groupID"];

//testing variables
#$SQLgroupID = 1;

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query obtains messages for the specified group
	$q = "SELECT * FROM messages WHERE groupID = $SQLgroupID";
// 	echo $q;
// 	echo "<br><br><br>";
	
	//retrieve groups from mySQL DB
	$result = mysqli_query($conn, $q);
	
	//check for empty result
	if (!empty($result)) {
		if (mysqli_num_rows($result) > 0) {
			
			//initialise array for returned messages
			$dbdata = array();
			
			//fill array with returned data rows
			while ($row = mysqli_fetch_assoc($result)) {
				$dbdata[]=$row;
			}
			
			//successful
			$response["success"] = 1;
			//add dbdata to response
			$response["messages"] = $dbdata;
			
			//return JSON response
			echo json_encode($response);
		}
		else {
			//no messages found
			$response["success"] = 0;
			$response["message"] = "No messages found";
			
			//return JSON response (unsuccessful)
			echo json_encode($response);
		}
	}
	else {
		//no messages found
		$response["success"] = 0;
		$response["message"] = "No messages found";
		
		//return JSON response (unsuccessful)
		echo json_encode($response);
	}
}
else {
	echo "CONNECTION FAILED";
}

?>