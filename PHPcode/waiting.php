<?php
$conn = mysqli_connect("localhost", "root", "chamel186", "breaktime");
if(!$conn)
{
die("Database connection failed: " .mysqli_error($conn));
exit();
}
$ID=$_GET['ID'];

$sql = $conn->query("SELECT ready from Game WHERE Game.id = (SELECT from player WHERE nickname = '$ID')");
$row = mysqli_fetch_assoc($sql);
echo $row['ready'];

mysqli_close($conn);

?>
