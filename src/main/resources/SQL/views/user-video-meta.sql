
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
	    ),
		w."source"
	FROM "userview" AS w
	GROUP BY
	    w."user_login",
	    w."video_title",
		w."source"
