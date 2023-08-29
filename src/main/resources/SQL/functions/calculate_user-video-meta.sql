CREATE OR REPLACE FUNCTION CALCULATE_UVM(TIMEOFFSET
INTERVAL) RETURNS TABLE("user_login" TEXT, "video_title"
TEXT, "score" FLOAT8, "lastTimeView" timestamp, "timesPrompted"
integer) AS
$$
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
	    )
	FROM "userview" AS w
	WHERE
	    w."timestamp" >= (NOW() - timeoffset)
	GROUP BY
	    w."user_login",
	    w."video_title" $$ LANGUAGE
SQL;

CREATE OR REPLACE FUNCTION CALCULATE_UVM() RETURNS
TABLE("user_login" TEXT, "video_title" TEXT, "score"
FLOAT8, "lastTimeView" timestamp, "timesPrompted"
integer) AS
$$
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
	    )
	FROM "userview" AS w
	GROUP BY
	    w."user_login",
	    w."video_title" $$ LANGUAGE
SQL;
