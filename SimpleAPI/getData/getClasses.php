<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');
// 데이터베이스 연결 설정
$host = "";
$dbUsername = "";
$dbPassword = "";
$dbName = "";

// MySQL 연결
$con = mysqli_connect($host, $dbUsername, $dbPassword, $dbName);

// 연결 확인
if (mysqli_connect_errno()) {
    die("Database connection failed: " . mysqli_connect_error());
}

// JSON 응답을 위한 헤더 설정
header('Content-Type: application/json');
$content = trim(file_get_contents("php://input"));
$decoded = json_decode($content, true);

// 요청 데이터 검증
if (!isset($decoded['topicId'])) {
    echo json_encode(array("success" => false, "message" => "something is missing"));
    exit;
}

$topicId = $decoded['topicId'];

// 해당 topicID의 클래스 목록 조회
$query = "SELECT classname FROM classes WHERE topic_id = '$topicId'";
$result = mysqli_query($con, $query);

$classes = array();
while($row = mysqli_fetch_assoc($result)) {
    array_push($classes, $row['classname']);
}

echo json_encode($classes);


mysqli_close($con);
?>