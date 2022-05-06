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
#$SQLgroupID = 2;

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query obtains join reqeusts for the specified group (that have not already been responded to)
	$q = "SELECT * FROM requests WHERE groupID = $SQLgroupID AND response IS NULL";
// 	echo $q;
// 	echo "<br><br><br>";
	
	//retrieve groups from mySQL DB
	$result = mysqli_query($conn, $q);
	
	//check for empty result
	if (!empty($result)) {
		if (mysqli_num_rows($result) > 0) {
			
			//initialise array for returned join requests
			$dbdata = array();
            //initialise array for returned users (senders of each request)
            $dbdata2 = array();
			
			//fill array with returned data rows
			while ($row = mysqli_fetch_assoc($result)) {
				$dbdata[]=$row; //requests
                
                $senderID = $row['senderID'];
                #echo $senderID;
                
                $result2 = mysqli_query($conn, "SELECT id, username, location, bio, interests FROM users WHERE id = $senderID");
                
                //get sender details for this request
                while ($row = mysqli_fetch_assoc($result2)) {
                    #echo $row['username'];
                    $dbdata2[]=$row; //users
                }
			}
			
			//successful
			$response["success"] = 1;
			//add dbdata to response
			$response["requests"] = $dbdata;
            //add dbdata2 to response
			$response["users"] = $dbdata2;
			
			//return JSON response
			echo json_encode($response);
            
            echo "<br><br><br>";
            
            //results were successfully returned, set 'seen' to 1 for returned join reqeuests
            $sql = "UPDATE requests SET seen = 1 WHERE groupID = $SQLgroupID AND response IS NULL";
            
            if ($conn->query($sql) === TRUE) {
                echo "Records updated successfully";
            } else {
                echo "Error updating record: " . $conn->error;
            }
		}
		else {
			//no messages found
			$response["success"] = 0;
			$response["message"] = "No requests found";
			
			//return JSON response (unsuccessful)
			echo json_encode($response);
		}
	}
	else {
		//no messages found
		$response["success"] = 0;
		$response["message"] = "No requests found";
		
		//return JSON response (unsuccessful)
		echo json_encode($response);
	}
}
else {
	echo "CONNECTION FAILED";
}

?>