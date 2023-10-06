CREATE TABLE IF NOT EXISTS "SuggestionBase"
(
    "user_login" text COLLATE pg_catalog."default" NOT NULL,
    "video_title" text COLLATE pg_catalog."default" NOT NULL,
    score double precision NOT NULL,
    CONSTRAINT "SuggestionBase_pkey" PRIMARY KEY ("user_login", "video_title"),
    CONSTRAINT "SuggestionBase_userLogin_fkey" FOREIGN KEY ("user_login")
        REFERENCES public."users" (login) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT "SuggestionBase_videoTitle_fkey" FOREIGN KEY ("video_title")
        REFERENCES public."video" (title) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)
