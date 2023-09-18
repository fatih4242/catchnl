//
//  NotificationViewModel.swift
//  catchnl
//
//  Created by Fatih Toker on 15.09.2023.
//

import Foundation

class NotificationViewModel: ObservableObject {
    @Published var sendedNotifications: [Complaint] = []
    @Published var receivedNotifications: [Complaint] = []
    
    
    init(user: User) {
        getComplaints(user: user)
    }
    
    private func getComplaints(user: User){
        API().MakeRequest(endpoint: URLState.getComplaints.rawValue, parameters: [:]) { data in
            let complaints = try! JSONDecoder().decode([Complaint].self, from: data)
            self.sendedNotifications = complaints.filter{ $0.userID == user.id }
            self.receivedNotifications = complaints.filter{ $0.licencePlate == user.licencePlate }
            
            print(self.sendedNotifications)
            
        } onError: { error in
            print(error)
        }

    }
}
