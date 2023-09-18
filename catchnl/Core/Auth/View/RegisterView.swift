//
//  LoginView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI
import PhotosUI

struct RegisterView: View {
    @EnvironmentObject var contentViewModel : ContentViewModel
    @StateObject var authViewModel = AuthViewModel()
    
    var body: some View {
        NavigationView {
            switch authViewModel.state {
            case .nothing:
                ScrollView(.vertical){
                    Spacer()
                   
                    
                    
                    Image("logo-dark")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 200)
                        .padding(.bottom)
                                
                    Text("Meld je aan")
                        .font(.custom(Font.Poppins_SemiBold, size: 16))
                    
                    Text("Wordt lid!")
                        .font(.custom(Font.Poppins_Regular, size: 14))
                        
                    ZStack(alignment: .center){
                        Image("licencePlate")
                            .resizable()
                            .scaledToFit()
                        
                        TextField("", text: $authViewModel.licencePlate)
                            .font(.custom(Font.Poppins_SemiBold, size: 30))
                            .keyboardType(.URL)
                            .textInputAutocapitalization(.characters)
                            .padding(.leading, 70)
                    }
                 
                    
                    VStack{
                        InputCellView(hint: "Naam",systemImageName: "person.circle" , text: $authViewModel.name, state: .text)
                            .padding(.horizontal)
                        
                        InputCellView(hint: "Email",systemImageName: "phone" , text: $authViewModel.email, state: .text)
                            .padding(.horizontal)
                        
                        InputCellView(hint: "Wachtwoord",systemImageName: "lock" , text: $authViewModel.password, state: .password)
                            .padding(.horizontal)
                        
                        

                        
                        PhotosPicker("Een foto van uw Kentekenbewijs", selection: $authViewModel.selectedItem, matching: .images)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.leading)
                        if let image = authViewModel.selectedImage {
                            image
                                .resizable()
                                .scaledToFit()
                                .frame(width: 300, height: 300)
                        }
                    }
                    .onChange(of: authViewModel.selectedItem) { _ in
                                Task {
                                    if let data =
                                        try? await authViewModel.selectedItem?.loadTransferable(type: Data.self) {
                                        authViewModel.selectedImageData = data
                                        if let uiImage = UIImage(data: data) {
                                            authViewModel.selectedImage = Image(uiImage: uiImage)
                                            return
                                        }
                                    }
                                    print("Failed")
                                }
                            }
                    
        
                    Button {
                        authViewModel.state = .loading
                        authViewModel.signUp{ isSuccess in
                            if isSuccess {
                                contentViewModel.checkIfUserExists()
                                authViewModel.state = .nothing
                            } else {
                                print("error while register")
                                authViewModel.state = .nothing
                            }
                            
                        }
                    } label: {
                        Text("Registreer")
                            .modifier(ButtonModifier())
                            .font(.custom(Font.Poppins_SemiBold, size: 18))
                    }
                    
                    HStack(spacing: 2){
                        Text("Heb je al een account?")
                            .font(.custom(Font.Poppins_Regular, size: 14))
                        NavigationLink {
                            LoginView()
                                .navigationBarBackButtonHidden(true)
                        } label: {
                            Text("Aanmelden")
                                .font(.custom(Font.Poppins_Regular, size: 14))
                                .foregroundColor(Color("primary-color"))
                        }
                    }
                    .padding(.top)
                                    
                    Spacer()
                    
                }
                .padding(.horizontal, 5)
                .alert(authViewModel.errorMessage, isPresented: $authViewModel.isError) {
                            Button("OK", role: .cancel) { }
                }
                
            case .loading:
                ProgressView()
                
            }
        
        }
    
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}
