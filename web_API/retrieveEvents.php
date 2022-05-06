<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//user id from POST
$SQLuserID = $_POST["userID"];

//testing variables
#$SQLuserID = 3;

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

//change character set to utf8 - without this the JSON encoding failed
mysqli_set_charset($conn,"utf8");

if($conn) {
	//connection was successful
	
	//SQL query to retrive this users interests from the DB
	$q = "SELECT interests FROM users WHERE id = $SQLuserID";
	$result = mysqli_query($conn, $q); //returns only one user
	
	//save users interests as a String initially
	while ($row = mysqli_fetch_assoc($result)) {
		$interestsString = $row['interests'];
	}
// 	echo $interestsString;
	
	//explode interestsString to array (so we can reformat it after)
	$interestsArray = explode(",", $interestsString);
// 	print_r($interestsArray);
	
// 	echo "<br><br><br>";
	
	//finally, implode the array into a String and concatenate to get correct SQL syntax
	$interestsFormatted = "'" . implode("', '", $interestsArray) . "'";
// 	echo $interestsFormatted;
	
	//array for JSON response
	$response = array();
	
	//SQL query obtains events that match the formatted interests
	$q = "SELECT * FROM events WHERE category IN ($interestsFormatted)";
// 	echo $q;
// 	echo "<br><br><br>";
	
	//retrieve events from mySQL DB
	$result = mysqli_query($conn, $q);
	
	//check for empty result
	if (!empty($result)) {
		if (mysqli_num_rows($result) > 0) {
			
			//initialise array for returned events
			$dbdata = array();
            //initialise array to return the venue for each event
            $dbdata2 = array();
			
			//fill array with returned data rows
			while ($row = mysqli_fetch_assoc($result)) {
				$dbdata[]=$row;
                $venueID = $row['venueID'];
                #echo $venueID;
                //get venue for this event
                $result2 = mysqli_query($conn, "SELECT * FROM venues WHERE id = $venueID");
                while ($row = mysqli_fetch_assoc($result2)) {
                    #echo $row['name'];
                    $dbdata2[]=$row;
                }
                #echo "<br><br>";
			}
            #echo $dbdata2[0];
            #foreach ($dbdata2 as $var) {echo $var;}
			
			//successful
			$response["success"] = 1;
			//add dbdata to response
			$response["events"] = $dbdata;
            //add dbdata2 to response
            $response["venues"] = $dbdata2;
			
			//return JSON response
			echo json_encode($response);
		}
		else {
			//no events found
			$response["success"] = 0;
			$response["message"] = "No events found";
			
			//return JSON response (unsuccessful)
			echo json_encode($response);
		}
	}
	else {
		//no events found
		$response["success"] = 0;
		$response["message"] = "No events found";
		
		//return JSON response (unsuccessful)
		echo json_encode($response);
	}
}
else {
	echo "CONNECTION FAILED";
}

?>