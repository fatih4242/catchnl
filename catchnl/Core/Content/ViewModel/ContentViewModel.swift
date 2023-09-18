//
//  ContentViewModel.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import Foundation
import Network

enum ContentViewEnum{
    case noWifi
    case info
    case splash
    case notAccepted
    case registerView
    case loginView
    case main
}

class ContentViewModel: ObservableObject {
    @Published var state: ContentViewEnum  = .splash
    @Published var currentUser: User = User(id: "", name: "", email: "", password: "", licencePlate: "", licenceFile: "", isAccepted: "")
    @Published var goInfoFromMain = false
    
    init(){
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            self.checkIfUserExists()
        }

        
    }
    
    func updateProfile() {
        let email = UserDefaults.standard.string(forKey: "email")!
        let password = UserDefaults.standard.string(forKey: "password")!
        
        let parameter = [
            "email" : email,
            "password" : password
        ]  as [String : Any]
        
        API().MakeRequest(endpoint: URLState.login.rawValue, parameters: parameter) { data in
            let user = try! JSONDecoder().decode(User.self, from: data)
            if user.error == 0 {
                self.currentUser = user
                print("current month remain \(self.currentUser.complaintPerMonthLeft ?? "nil")")
            } else {
                print("error while updating month remaining")
            }
        } onError: { error in
            print(error)
        }
    }
    
    func checkIfUserExists(){
        
        let monitor = NWPathMonitor()

        monitor.pathUpdateHandler = { path in
           if path.status == .satisfied {
               self.makeRequest()
               monitor.cancel()
           } else {
               self.state = .noWifi
           }
           print(path.isExpensive)
        }
        let queue = DispatchQueue(label: "Monitor")
        monitor.start(queue: queue)

    }
    private func makeRequest(){
        if UserDefaults.standard.string(forKey: "email") != nil
            && UserDefaults.standard.string(forKey: "password") != nil {
            let email = UserDefaults.standard.string(forKey: "email")!
            let password = UserDefaults.standard.string(forKey: "password")!
            
            let parameter = [
                "email" : email,
                "password" : password
            ]  as [String : Any]
            
          

            API().MakeRequest(endpoint: URLState.login.rawValue, parameters: parameter) { data in
                let user = try! JSONDecoder().decode(User.self, from: data)
                print("check for request")
                
                if user.error! == 0 {
                    self.currentUser = user
                    print(user)
                    if user.isAccepted! == "false" {
                        print("isAccepted false")
                        self.state = .notAccepted
                    } else {
                        print("isAccepted true")
                        self.state = .main
                    }
                } else {
                    print("error user")
                    self.state = .loginView
                }
                 
            } onError: { error in
                print(error)
            }

            
        } else {
            state = .loginView
        }
    }
}
