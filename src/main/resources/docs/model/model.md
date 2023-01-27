# Модель данных

## user

id, nickname, password, access_token, time_insert

## phrase

id, user_id, text, time_insert

## tag

id, text, time_insert

## phrase_tag

id, phrase_id, tag_id, time_insert

## subscription

id, sub_user_id, pub_user_id, time_insert

## like_phrase

id, phrase_id, user_id, time_insert

## comment

id, user_id, phrase_id, text, time_insert