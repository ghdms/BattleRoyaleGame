<?php
$conn = mysqli_connect("localhost", "root", "chamel186", "breaktime");
if(!$conn)
{
die("Database connection failed: " .mysqli_error($conn));
exit();
}
$ID=$_GET['ID'];
$sql = $conn->query("SELECT hp, fight, zone FROM player WHERE nickname = '$ID'");
$sql2 = $conn->query("SELECT * FROM player WHERE room = (SELECT room FROM player WHERE nickname = '$ID')");

$row = mysqli_fetch_assoc($sql);
$row2 = mysqli_num_rows($sql2);
echo $row['hp'].' '.$row['fight'].' '.$row['zone'].' ';
echo $row2;

mysqli_close($conn);

?>
