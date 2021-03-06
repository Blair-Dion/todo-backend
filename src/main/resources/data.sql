INSERT INTO user (user_id, user_nickname, github_token, profile_image_url, created_datetime,
                  updated_datetime)
VALUES ('ksundong', 'Dion', 'token',
        'https://avatars0.githubusercontent.com/u/38597469?s=88&u=4dec19ec378bfb64c9b4a00c4d63e7805dac9c6c&v=4',
        NOW(), NOW());

INSERT INTO board (id, name, created_datetime, updated_datetime, created_by, updated_by)
VALUES (1, 'Board', NOW(), NOW(), 1, 1);

INSERT INTO list (id, name, is_archived, archived_datetime, board_id, created_datetime, created_by,
                  updated_datetime, updated_by)
VALUES (1, 'TODO', false, null, 1, NOW(), 1, NOW(), 1),
       (2, 'DOING', false, null, 1, NOW(), 1, NOW(), 1),
       (3, 'DONE', false, null, 1, NOW(), 1, NOW(), 1);

INSERT INTO card (title, contents, pos, is_archived, archived_datetime, list_id, user_id,
                  created_datetime, created_by, updated_datetime, updated_by)
VALUES ('콩이 놀아주기', '재밌게 놀아주기', 0, false, null, 1, 1, NOW(), 1, NOW(), 1),
       ('Todo Project', '이번주까지 열심히 만들기', 0, false, null, 2, 1, NOW(), 1, NOW(), 1),
       ('취업하기', '성장할 수 있는 회사가기', 0, false, null, 3, 1, NOW(), 1, NOW(), 1);

INSERT INTO log (type, before_title, after_title, before_contents, after_contents, from_list_id,
                 to_list_id, board_id,
                 created_datetime, created_by, updated_datetime, updated_by)
VALUES ('LIST_ADD', null, null, null, null, null, 1, 1, NOW(), 1, NOW(), 1),
       ('LIST_ADD', null, null, null, null, null, 2, 1, NOW(), 1, NOW(), 1),
       ('LIST_ADD', null, null, null, null, null, 3, 1, NOW(), 1, NOW(), 1),
       ('CARD_ADD', null, '콩이 놀아주기', null, '재밌게 놀아주기', 1, 1, 1, NOW(), 1, NOW(), 1),
       ('CARD_ADD', null, 'Todo Project', null, '이번주까지 열심히 만들기', 2, 2, 1, NOW(), 1, NOW(), 1),
       ('CARD_ADD', null, '취업하기', null, '성장할 수 있는 회사가기', 3, 3, 1, NOW(), 1, NOW(), 1);
