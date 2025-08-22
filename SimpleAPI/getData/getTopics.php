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
if (!isset($decoded['username'])) {
    echo json_encode(array("success" => false, "message" => "username is missing"));
    exit;
}
// POST 방식으로 username 받기
$user_username = mysqli_real_escape_string($con, $decoded['username']);

// 쿼리 실행
$query = "SELECT id, topic, count FROM user_topics  WHERE username = '$user_username'";
$result = mysqli_query($con, $query);
// 결과를 배열로 변환
$topics = array();
while ($row = $result->fetch_assoc()) {
    array_push($topics, $row);
}

// JSON으로 변환하여 출력
echo json_encode($topics);

// 연결 종료
mysqli_close($con);
?>