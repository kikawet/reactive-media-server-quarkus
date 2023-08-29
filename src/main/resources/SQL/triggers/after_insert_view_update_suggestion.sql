CREATE OR REPLACE FUNCTION after_insert_view_update_suggestion_tigger_function() RETURNS TRIGGER AS $$
DECLARE
	min_views integer;
	rand float8;
	shrunk_score float8;
	uvmr "UserVideoMeta"%ROWTYPE;
BEGIN
	DELETE FROM "SuggestionBase" AS s WHERE s."user_login" = NEW."user_login" and s."video_title" = NEW."video_title";
	SELECT MIN("timesPrompted") INTO min_views FROM "UserVideoMeta" WHERE "user_login" = NEW."user_login";
	SELECT * INTO uvmr FROM "UserVideoMeta" AS uvm WHERE uvm."user_login" = NEW."user_login" and uvm."video_title" = NEW."video_title";

	rand = random();
	shrunk_score = (uvmr."score" - uvmr."timesPrompted" + min_views)/100;

	IF rand < shrunk_score THEN
		INSERT INTO "SuggestionBase" VALUES (NEW."user_login",NEW."video_title", uvmr."score"/100);
	ELSE
		INSERT INTO "SuggestionBase" VALUES (NEW."user_login",NEW."video_title", uvmr."score"/100-1);
	END IF;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER after_insert_view_update_suggestion_tigger
AFTER INSERT ON "userview" FOR EACH ROW EXECUTE FUNCTION after_insert_view_update_suggestion_tigger_function();
