<?php 	

// koneksi database
include 'conn.php';

// query data
$sql = "SELECT * FROM data_sensor";
$query = mysqli_query($conn, $sql);

while ($data = mysqli_fetch_array($query)) {
	 // print_r($data);
	 // echo $data["suhu"];

	$item = [
		"suhu" => $data["suhu"],
		"kelembapan" => $data["kelembapan"],
		"waktu" => $data["waktu"],
	];

	$response = [
		"status" => "OK",
		"data" => $item,

	];

	echo "<pre>";
	echo json_encode($response);
	echo "</pre>";
}

?>