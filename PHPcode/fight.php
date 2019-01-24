<?php
$conn = mysqli_connect("localhost", "root", "chamel186", "breaktime");
if(!$conn)
{
die("Database connection failed: " .mysqli_error($conn));
exit();
}
$ID=$_GET['ID'];
$shake = $_GET['shake'];

$sql = $conn->query(""UPDATE fight SET shake = '$shake' WHERE pl_name = '$ID');
$sql2 = $conn->query("UPDATE player SET fight = 2 WHERE nickname = '$ID'");

if(!$sql || !$sql2)
{
echo 'error';
}
else
{
echo 'ok';
}

mysqli_close($conn);

?>
