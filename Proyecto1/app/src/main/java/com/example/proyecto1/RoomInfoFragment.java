package com.example.proyecto1;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RoomInfoFragment extends DialogFragment {
    private static final String ARG_ROOM_NAME = "ROOM_NAME";

    public static RoomInfoFragment newInstance(String roomName) {
        RoomInfoFragment fragment = new RoomInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROOM_NAME, roomName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_info, container, false);

        String roomName = getArguments().getString(ARG_ROOM_NAME, "Ambiente");
        TextView titleView = view.findViewById(R.id.roomTitle);
        titleView.setText(roomName);

        TextView descriptionView = view.findViewById(R.id.roomDescription);
        String description = getRoomDescription(roomName);
        descriptionView.setText(description);

        return view;
    }

    private String getRoomDescription(String roomName) {
        switch (roomName) {
            case "Room 1":
                return "Descripción del Room 1";
            case "Room 2":
                return "Descripción del Room 2";
            case "Patio 1":
                return "Descripción del Patio 1";
            case "Patio 2":
                return "Descripción del Patio 2";
            default:
                return "Información no disponible";
        }
    }
}