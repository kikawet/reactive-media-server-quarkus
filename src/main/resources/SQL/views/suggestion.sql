CREATE OR REPLACE VIEW "Suggestion" AS

SELECT * FROM "SuggestionBase"
UNION ALL
SELECT *, random() as "score" FROM "NonView"
