DROP TABLE IF EXISTS "public"."tenants";
CREATE TABLE "public"."tenants" (
  "id" varchar(100) NOT NULL,
  "tenantname" varchar(50) NOT NULL,
  "description" varchar(500)
);

DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" varchar(100) NOT NULL,
  "username" varchar(50) NOT NULL,
  "passwords" varchar(100) NOT NULL,
  "tenantid" varchar(100) NULL,
  "email" varchar(50),
  "fullname" varchar(80),
  "telephone" varchar(20),
  "description" varchar(500),
  "createtime" timestamp(6),
  "modifytime" timestamp(6),
  "creator" varchar(50)
);

DROP TABLE IF EXISTS "public"."userrole";
CREATE TABLE "public"."userrole" (
  "userid" varchar(100) NOT NULL,
  "roleid" varchar(100) NOT NULL
);

DROP TABLE IF EXISTS "public"."roles";
CREATE TABLE "public"."roles" (
  "id" varchar(100) NOT NULL,
  "rolename" varchar(50) NOT NULL,
  "description" varchar(500)
);

DROP TABLE IF EXISTS "public"."roleoperset";
CREATE TABLE "public"."roleoperset" (
  "roleid" varchar(100) NOT NULL,
  "opersetid" varchar(100) NOT NULL
);

DROP TABLE IF EXISTS "public"."opersets";
CREATE TABLE "public"."opersets" (
  "id" varchar(100) NOT NULL,
  "opersetname" varchar(50) NOT NULL,
  "description" varchar(500)
);