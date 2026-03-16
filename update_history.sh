#!/bin/bash
# History Entry Helper Script
# Usage: ./update_history.sh "Entry title" "User request" "Assistant action" "Result" "Status"

HISTORY_FILE=".ai-history.md"

if [ $# -lt 2 ]; then
    echo "Usage: ./update_history.sh \"title\" \"user_request\" [\"action\"] [\"result\"] [\"status\"]"
    echo ""
    echo "Example:"
    echo "./update_history.sh \"User Has Native Libraries\" \"I have .so files\" \"Asked for file path\" \"User confirmed arm64-v8a\" \"Waiting for path\""
    exit 1
fi

TITLE="$1"
USER_REQ="$2"
ACTION="${3:-}"
RESULT="${4:-}"
STATUS="${5:-In Progress}"

# Get last entry number
LAST_ENTRY=$(grep -oP "## Entry \K[0-9]+" "$HISTORY_FILE" 2>/dev/null | tail -1)
if [ -z "$LAST_ENTRY" ]; then
    LAST_ENTRY=0
fi
NEW_ENTRY=$((LAST_ENTRY + 1))

# Get current timestamp
TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

# Create new entry
NEW_ENTRY_TEXT="
---

## Entry $NEW_ENTRY: $TITLE

- **User:** \"$USER_REQ\"
"

if [ -n "$ACTION" ]; then
    NEW_ENTRY_TEXT+="- **Assistant Action:** $ACTION
"
fi

if [ -n "$RESULT" ]; then
    NEW_ENTRY_TEXT+="- **Result:** $RESULT
"
fi

NEW_ENTRY_TEXT+="- **Status:** $STATUS
"

# Find the last \"---\" before \"Last Updated\" section
LAST_SEPARATOR=$(grep -n "^---$" "$HISTORY_FILE" | tail -1 | cut -d: -f1)

if [ -n "$LAST_SEPARATOR" ]; then
    # Insert before the last separator (before Last Updated section)
    head -n $((LAST_SEPARATOR - 1)) "$HISTORY_FILE" > /tmp/history_temp.md
    echo "$NEW_ENTRY_TEXT" >> /tmp/history_temp.md
    echo "" >> /tmp/history_temp.md
    tail -n +$LAST_SEPARATOR "$HISTORY_FILE" >> /tmp/history_temp.md
    mv /tmp/history_temp.md "$HISTORY_FILE"
    echo "✓ Entry $NEW_ENTRY added: $TITLE"
else
    # Append to end of file
    echo "$NEW_ENTRY_TEXT" >> "$HISTORY_FILE"
    echo "---" >> "$HISTORY_FILE"
    echo "✓ Entry $NEW_ENTRY added: $TITLE"
fi

# Update the Last Updated timestamp and entry count
sed -i "s/\*\*Last Updated:\*\*.*/\*\*Last Updated:\*\* $TIMESTAMP/" "$HISTORY_FILE"
sed -i "s/\*\*Total Entries:\*\*.*/\*\*Total Entries:\*\* $NEW_ENTRY/" "$HISTORY_FILE"

echo "✓ History file updated with entry $NEW_ENTRY"
