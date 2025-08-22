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
if (!isset($decoded['topicId']) && !isset($decoded['className'])) {
    echo json_encode(array("success" => false, "message" => "something is missing"));
    exit;
}
// POST 방식으로 username 받기
$topic_id = mysqli_real_escape_string($con, $decoded['topicId']);
$class = mysqli_real_escape_string($con, $decoded['className']);

// 공백 문자만 있는지 확인
if (ctype_space($class) || $class === "") {
    echo json_encode(array("success" => false, "message" => "only whitespace"));
    exit;
}

// 동일한 클래스명이 있는지 확인
$query = "SELECT * FROM classes WHERE topic_id = '$topic_id' AND classname = '$class'";
$result = mysqli_query($con, $query);

if (mysqli_num_rows($result) > 0) {
    // 중복된 클래스명이 있음
    echo json_encode(array("success" => false, "message" => "already exists"));
} else {
    // 중복된 클래스명이 없으므로 삽입
    $insertQuery = "INSERT INTO classes (topic_id, classname) VALUES ('$topic_id', '$class')";
    if (mysqli_query($con, $insertQuery)) {
        echo json_encode(array("success" => true, "message" => "Class added successfully"));
    } else {
        echo json_encode(array("success" => false, "message" => "Error adding class"));
    }
}

mysqli_close($con);
?>