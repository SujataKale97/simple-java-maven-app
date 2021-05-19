## This terraform template provisions Amazon S3 Bucket and Amazon CloudFront web Distribution.

### Usage
1. Terraform Init : Initializes a working directory containing Terraform configuration files.
	`terraform init`
	
2. Terraform Plan : Creates an execution plan.
	`terraform plan -var-file="dev.tfvars" -out="dev.tfstate"`
	 Select variable and state file as per environment. If this matches your expectation, you can confirm resource creation using apply.
	
3. Terraform Apply : Executes the actions proposed in a Terraform plan.
    `terraform apply -var-file="dev.tfvar" -state-out="dev.tfstate"`
	
### Requirements

| Name | Version |
|------|---------|
| terraform | >= 0.14.7 |

### Providers

| Name | Version |
|------|---------|
| aws | >= 3.35.0 |

### Modules

| Sr. No. | Module Name             |
|---------|-------------------------|            
| 1       | S3 Bucket               |
| 2       | CloudFront Distribution |

We can call these modules as many times we need (reusability). Here we have created 3 s3 buckets with 3 cloudfront distributions by invkoing S3_bucket and CloudfrontDistribution module 3 times in main.tf. Just we need to pass these many bucket names in variable section and call the modules by doing proper mapping of output variables of S3_bucket module with input variables of CloudfrontDistribution module.  


## Resources

| Name | Type |
|------|------|
| [aws_s3_bucket](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/s3_bucket) | resource |
| [aws_s3_bucket_public_access_block](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/s3_bucket_public_access_block) | resource |
| [aws_s3_bucket_policy](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/s3_bucket_policy) | resource |
| [aws_iam_policy_document](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/iam_policy_document) | data source |
| [aws_cloudfront_origin_access_identity](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/cloudfront_origin_access_identity) | resource |
| [aws_cloudfront_distribution](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/cloudfront_distribution) | resource |

# Inputs & Outputs

### Inputs for main module

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|----------|
| account_id | AWS account ID | `string` | `null` | Yes |
| region | This is the AWS region | `string` | `us-east-1` | No |
| profile | Environment profile | `string` | `dev` | No |


## Module 1. Amazon S3 Bucket

### Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|----------|
| bucket_name | The name of the bucket | `string` | `null` | Yes |

### Outputs

| Name | Description | Type |
|------|-------------|------|
| bucket_arn | To be referenced in CloudFront Distribution | `string` |
| s3_origin_id | To be referenced in CloudFront Distribution | `string` |
| website_endpoint | To be referenced in CloudFront Distribution | `string` |
| s3_bucket_domain_name | To be referenced in CloudFront Distribution | `string` |
| cloudfront_access_identity_path | To be referenced in CloudFront Distribution | `string` |

## Module 2. CloudFront Distribution

### Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|----------|
| s3_bucket_domain_name | No need to pass from variable file. It will be referenced from output of S3Bucket module | `string` | `module.S3_Bucket.s3_bucket_domain_name` | No |
| s3_origin_id |  No need to pass from variable file. It will be referenced from output of S3Bucket module | `string` | `module.S3_Bucket.s3_origin_id` | No |
| cloudfront_access_identity_path |  No need to pass from variable file. It will be referenced from output of S3Bucket module | `string` | `module.S3_Bucket.cloudfront_access_identity_path` | No |

