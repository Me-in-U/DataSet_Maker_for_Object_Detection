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
    echo json_encode(array("success" => false, "message" => "topicID is missing"));
    exit;
}
// POST 방식으로 topicID 받기
$topic_id = mysqli_real_escape_string($con, $decoded['topicId']);

// topic_id에 해당하는 이미지의 id 목록을 검색
$query = "SELECT id FROM topic_images WHERE topic_id = '$topic_id'";
$result = mysqli_query($con, $query);

// 결과를 배열로 변환
$idList = array();
while ($row = mysqli_fetch_assoc($result)) {
    array_push($idList, $row['id']);
}

// JSON으로 변환하여 출력
echo json_encode(array("success" => true, "idList" => $idList));

// 연결 종료
mysqli_close($con);
?>