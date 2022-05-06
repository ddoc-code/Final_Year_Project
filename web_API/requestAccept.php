<?php
//DB connection details
$db = "ddoch001";
$user = "ddoch001";
$pass = "temppass2";
$host = "igor.gold.ac.uk";
$port = "3306";

//requestID from post
$SQLrequestID = $_POST["requestID"];
$SQLsenderID = $_POST["senderID"];

//testing variables
// $SQLrequestID = 5;
// $SQLsenderID = 6;

//initiate DB connection
$conn = mysqli_connect($host, $user, $pass, $db, $port);

if($conn) {
	//connection was successful
    
    //get maxpeople, currentpeople, attendees for this group (via request)
    $res = mysqli_query($conn, "SELECT maxPeople, currentPeople, attendees FROM groups WHERE id = (SELECT groupID FROM requests WHERE id = $SQLrequestID)");
    while ($row = mysqli_fetch_assoc($res)) {
        //save as variables
        $currentPeople = $row['currentPeople'];
        $maxPeople = $row['maxPeople'];
        $attendees = $row['attendees'];
    }
    
    //print variables
//     echo $currentPeople . "/" . $maxPeople . " users:<br>";
//     echo $attendees;
    
    //if currentPeople < maxPeople, there is space in the group
    if ($currentPeople < $maxPeople) {
//         echo "<br>SPACE AVAILABLE<br>";
        
        //retrieve username of request sender
        $res2 = mysqli_query($conn, "SELECT username FROM users WHERE id = $SQLsenderID");
        while ($row = mysqli_fetch_assoc($res2)) {
            //save as variable
            $sender = $row['username'];
        }
        
//         echo $sender;
        
        //append sender to the list of attendees (use comma delimiter)
        $newAttendees = $attendees . "," . $sender;
        
//         echo "<br>" . $newAttendees;
        
        //array for JSON response
        $response = array();
        
        //SQL query to update attendees
        $q = "UPDATE groups SET attendees = '$newAttendees' WHERE id = (SELECT groupID FROM requests WHERE id = $SQLrequestID)";
        
        //check if attendees was updated successfully
        if (mysqli_query($conn, $q)) {
            //successful
            
            //SQL query to update currentPeople
            $q = "UPDATE groups SET currentPeople = ($currentPeople+1) WHERE id = (SELECT groupID FROM requests WHERE id = $SQLrequestID)";
            
            //check if currentPeople was updated successfully
            if (mysqli_query($conn, $q)) {
                //successful
                
                $q = "UPDATE requests SET response = 1 WHERE id = $SQLrequestID";
                mysqli_query($conn, $q);
                
                $response["success"] = 1;
                $response["message"] = "User added to group";
                
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
            
        }///
        else {
            //unsuccessful
            $response["success"] = 0;
            $response["message"] = mysqli_error($conn);

            //return JSON response
            echo json_encode($response);
        }
    }
}	
else {
	echo "CONNECTION FAILED";
}

?>