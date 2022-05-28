<?php 

	$servername = "localhost";
	$username = "id18791339_root";
	$password = "k\EOX&&v6lAt@FGA";
	$dbname = "id18791339_db_sensor";

	$conn = mysqli_connect($servername, $username, $password, $dbname);

	if (!$conn) {
		echo "koneksi gagal";
	}

?>
