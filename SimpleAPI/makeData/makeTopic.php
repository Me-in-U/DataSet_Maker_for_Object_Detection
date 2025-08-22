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
$user_topic = mysqli_real_escape_string($con, $decoded['topic']);

// 동일한 주제가 있는지 확인
$query = "SELECT * FROM user_topics WHERE username = '$user_username' AND topic = '$user_topic'";
$result = mysqli_query($con, $query);

if (mysqli_num_rows($result) > 0) {
    // 중복된 주제가 있음
    echo json_encode(array("success" => false, "message" => "already exists"));
} else {
    // 중복된 주제가 없으므로 삽입
    $insertQuery = "INSERT INTO user_topics (username, topic, count) VALUES ('$user_username', '$user_topic', 0)";
    if (mysqli_query($con, $insertQuery)) {
        echo json_encode(array("success" => true, "message" => "Topic added successfully"));
    } else {
        echo json_encode(array("success" => false, "message" => "Error adding topic"));
    }
}
mysqli_close($con);
?>