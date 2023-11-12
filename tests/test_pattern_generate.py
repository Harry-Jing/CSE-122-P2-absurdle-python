from src.absurdle_gan import pattern

test_words = [
    ("abbey", "bebop", "🟨🟨🟩⬜⬜"),
    ("abbey", "ether", "⬜⬜⬜🟩⬜"),
    ("abbey", "keeps", "⬜🟨⬜⬜⬜"),
    ("bebop", "abbey", "⬜🟨🟩🟨⬜"),
    ("gumball", "gumball", "🟩🟩🟩🟩🟩🟩🟩"),
    ("else", "meet", "⬜🟨🟨⬜"),
    ("willy", "troll", "⬜⬜⬜🟩🟨"),
    ("mummy", "madam", "🟩⬜⬜⬜🟨"),
    ("huhhu", "uhuuh", "🟨🟨🟨⬜🟨"),
]


def test_pattern():
    for word, guess, expected in test_words:
        assert (
            pattern(word, guess) == expected
        ), f"pattern({word}, {guess}) should be {expected}, but was {pattern(word, guess)}"
