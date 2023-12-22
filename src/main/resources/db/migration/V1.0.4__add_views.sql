-- View to get what videos each user has not seen yet
CREATE OR REPLACE VIEW "NonView" AS
SELECT u.login as "user_login", v.title as "video_title" FROM "users" as u, "video" as v
EXCEPT
SELECT DISTINCT "user_login", "video_title" from "userview";

-- View to merge all the suggestions into a single table
CREATE OR REPLACE VIEW "Suggestion" AS
SELECT * FROM "SuggestionBase"
UNION ALL
SELECT *, random() as "priority" FROM "NonView";

-- View used to group user views and give a score 
CREATE OR REPLACE VIEW "UserVideoMeta" AS
SELECT
	    w."user_login",
	    w."video_title",
	    ema(
	        w."completionpercentage"
	        ORDER BY
	            w."timestamp" DESC
	    ) AS "score",
	    MAX(w."timestamp") AS "lastTimeView",
	    COUNT(w."source") FILTER (
	        WHERE
	            w."source" = 'System'
	    ) AS "timesPrompted",
		w."source"
	FROM "userview" AS w
	GROUP BY
	    w."user_login",
	    w."video_title",
		w."source";