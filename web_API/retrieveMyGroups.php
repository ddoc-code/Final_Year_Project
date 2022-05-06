<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//event if from POST
$SQLuser = $_POST["user"];

//testing variables
#$SQLuser = "user2";

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//array for JSON response
	$response = array();
	
	//SQL query obtains groups for the specified event
// 	$q = "SELECT * FROM groups WHERE creator = '$SQLcreator'";
    $q = "SELECT * FROM groups WHERE attendees LIKE '%$SQLuser%'";
// 	echo $q;
// 	echo "<br><br><br>";
	
	//retrieve events from mySQL DB
	$result = mysqli_query($conn, $q);
	
	//check for empty result
	if (!empty($result)) {
		if (mysqli_num_rows($result) > 0) {
			
			//initialise array for returned groups
			$dbdata = array();
            //initialise array for returned events
			$dbdata2 = array();
            //initialise array for returned venues
            $dbdata3 = array();
			
			//fill array with returned data rows
			while ($row = mysqli_fetch_assoc($result)) {
				$dbdata[]=$row; //groups
                
                $eventID = $row['eventID'];
                #echo $eventID;
                
                $result2 = mysqli_query($conn, "SELECT * FROM events WHERE id = $eventID");
                
                $result3 = mysqli_query($conn, "SELECT * FROM venues WHERE id = (SELECT venueID FROM events WHERE id = $eventID)");
                
                //get event for this group
                while ($row = mysqli_fetch_assoc($result2)) {
                    #echo $row['name'];
                    $dbdata2[]=$row; //events
                }
                
                //get venue for this event
                while ($row = mysqli_fetch_assoc($result3)) {
                    #echo $row['name'];
                    $dbdata3[]=$row; //venues
                }
			}
			
			//successful
			$response["success"] = 1;
			//add dbdata to response
			$response["groups"] = $dbdata;
            //add dbdata2 to response
			$response["events"] = $dbdata2;
            //add dbdata3 to response
            $response["venues"] = $dbdata3;
			
			//return JSON response
			echo json_encode($response);
		}
		else {
			//no groups found
			$response["success"] = 0;
			$response["message"] = "No groups found";
			
			//return JSON response (unsuccessful)
			echo json_encode($response);
		}
	}
	else {
		//no groups found
		$response["success"] = 0;
		$response["message"] = "No groups found";
		
		//return JSON response (unsuccessful)
		echo json_encode($response);
	}
}
else {
	echo "CONNECTION FAILED";
}

?>