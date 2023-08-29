CREATE OR REPLACE VIEW "NonView" AS
SELECT u.login as "user_login", v.title as "video_title" FROM "users" as u, "video" as v
EXCEPT
SELECT DISTINCT "user_login", "video_title" from "userview"
