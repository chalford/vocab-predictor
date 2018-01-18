provider "aws" {
  access_key = "${var.access_key}"
  secret_key = "${var.secret_key}"
  region = "${var.region}"
}

# API Gateway

resource "aws_api_gateway_rest_api" "VocbabPredictorAPI" {
  name        = "VocbabPredictor"
  description = "A prototype API demonstrating different Linked Data embedding mechanisms."
}

resource "aws_api_gateway_resource" "VocbabPredictorResource" {
  rest_api_id = "${aws_api_gateway_rest_api.VocbabPredictorAPI.id}"
  parent_id   = "${aws_api_gateway_rest_api.VocbabPredictorAPI.root_resource_id}"
  path_part   = "predictions"
}

resource "aws_api_gateway_method" "VocbabPredictorMethod" {
  rest_api_id   = "${aws_api_gateway_rest_api.VocbabPredictorAPI.id}"
  resource_id   = "${aws_api_gateway_resource.VocbabPredictorResource.id}"
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "VocbabPredictorIntegration" {
  rest_api_id             = "${aws_api_gateway_rest_api.VocbabPredictorAPI.id}"
  resource_id             = "${aws_api_gateway_resource.VocbabPredictorResource.id}"
  http_method             = "${aws_api_gateway_method.VocbabPredictorMethod.http_method}"
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = "arn:aws:apigateway:${var.region}:lambda:path/2015-03-31/functions/${aws_lambda_function.VocbabPredictorRetriever.arn}/invocations"
}

resource "aws_api_gateway_deployment" "VocbabPredictorDevDeployment" {
  depends_on = [
    "aws_api_gateway_method.VocbabPredictorMethod",
    "aws_api_gateway_integration.VocbabPredictorIntegration"
  ]
  rest_api_id = "${aws_api_gateway_rest_api.VocbabPredictorAPI.id}"
  stage_name = "dev"
}

# Lambda

# Lambda permission, allow any calls on this Lambda from our specified API in API Gateway
resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.VocbabPredictorRetriever.function_name}"
  principal     = "apigateway.amazonaws.com"

  source_arn = "arn:aws:execute-api:${var.region}:${var.aws_account_id}:${aws_api_gateway_rest_api.VocbabPredictorAPI.id}/*"
}

resource "aws_lambda_function" "VocbabPredictorRetriever" {
  filename         = "vocab-predictor-retriever/target/vocab-predictor-retriever-1.0-SNAPSHOT.jar"
  function_name    = "VocbabPredictorRetriever"
  role             = "${aws_iam_role.VocbabPredictorLambdaRole.arn}"
  handler          = "uk.co.bbc.team12.VocbabPredictorRetriever"
  runtime          = "java8"
  source_code_hash = "${base64sha256(file("vocab-predictor-retriever/target/vocab-predictor-retriever-1.0-SNAPSHOT.jar"))}"

  //memory_size      = "256"
  //timeout          = "10"
}

# IAM
resource "aws_iam_role" "VocbabPredictorLambdaRole" {
  name = "VocbabPredictorLambdaRole"

  assume_role_policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": [
          "lambda.amazonaws.com",
          "apigateway.amazonaws.com",
          "sns.amazonaws.com"
        ]
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
POLICY
}

# Lambda policy for CloudWatch
resource "aws_iam_role_policy_attachment" "lambda_cloudwatchlogs_policy_attach" {
    role       = "${aws_iam_role.VocbabPredictorLambdaRole.name}"
    policy_arn = "arn:aws:iam::aws:policy/CloudWatchLogsFullAccess"
}

resource "aws_iam_role_policy" "S3LambdaAccessPolicy" {
    role   = "${aws_iam_role.VocbabPredictorLambdaRole.name}"
    policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["s3:ListBucket"],
      "Resource": ["arn:aws:s3:::plantuml-bot-diagrams"]
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:PutObject",
        "s3:GetObject",
        "s3:DeleteObject"
      ],
      "Resource": ["arn:aws:s3:::plantuml-bot-diagrams/*"]
    }
  ]
}
EOF
}

output "dev_url" {
  value = "https://${aws_api_gateway_deployment.VocbabPredictorDevDeployment.rest_api_id}.execute-api.${var.region}.amazonaws.com/${aws_api_gateway_deployment.VocbabPredictorDevDeployment.stage_name}"
}
