CREATE OR REPLACE FUNCTION after_insert_view_update_suggestion_tigger_function() RETURNS trigger AS $$
DECLARE
	min_views integer;
	rand float8;
	shrunk_priority float8;
	new_priority float8;
	uvmr "UserVideoMeta"%ROWTYPE;
BEGIN
	SELECT MIN("timesPrompted") INTO min_views FROM "UserVideoMeta" WHERE "user_login" = NEW."user_login";
	SELECT * INTO uvmr FROM "UserVideoMeta" AS uvm WHERE uvm."user_login" = NEW."user_login" and uvm."video_title" = NEW."video_title";

	rand = random();
	shrunk_priority = (uvmr."score" - uvmr."timesPrompted" + min_views)/100;
	new_priority = uvmr."score"/100;

	IF rand > shrunk_priority THEN
		new_priority = uvmr."score"/100-1;
	END IF;

	UPDATE "SuggestionBase" AS s SET priority = new_priority WHERE s."user_login" = NEW."user_login" and s."video_title" = NEW."video_title";
	
	IF NOT FOUND THEN 
		INSERT INTO "SuggestionBase" VALUES (NEW."user_login",NEW."video_title", new_priority);
	END IF;

	RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER after_insert_view_update_suggestion_tigger
AFTER INSERT ON "userview" FOR EACH ROW EXECUTE FUNCTION after_insert_view_update_suggestion_tigger_function();
