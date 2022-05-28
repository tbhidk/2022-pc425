<?php 

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "db_sensor";

	$conn = mysqli_connect($servername, $username, $password, $dbname);

	if (!$conn) {
		echo "koneksi gagal";
	}

?>