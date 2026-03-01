# Hotel Management AI Chatbot (SDA Project)

This repository contains a Software Design and Analysis (SDA) course project:
an AI-powered hotel management chatbot. The chatbot helps users check room
availability and manage basic bookings through a conversational interface.

## Features (current / planned)

- [x] Basic FastAPI backend
- [x] Simple rule-based chatbot engine
- [x] In-memory room and booking management
- [ ] Integration with a real LLM API
- [ ] Persistent database for rooms and bookings
- [ ] Authentication / user accounts
- [ ] Admin interface for hotel staff

## Project Structure

- `src/main.py` – FastAPI app entrypoint
- `src/api/` – HTTP endpoints (`/chat`, `/booking`)
- `src/models/` – Data models (Room, Booking, etc.)
- `src/services/` – Business logic (room and booking management)
- `src/chatbot/` – Chatbot engine and intents
- `docs/` – Proposal, SRS, design notes, and use cases

## Running the project

```bash
pip install -r requirements.txt
uvicorn src.main:app --reload
